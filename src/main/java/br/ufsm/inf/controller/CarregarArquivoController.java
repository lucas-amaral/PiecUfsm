package br.ufsm.inf.controller;

import br.ufsm.inf.model.ArquivoTemporario;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipInputStream;

/**
 * Created by Lucas on 31/10/2014.
 */
@Controller
@SessionAttributes("arquivo")
public class CarregarArquivoController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping("/carregar-arquivo.htm")
    public void apresentaArquivo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String id = httpServletRequest.getParameter("id");
        String abrir = httpServletRequest.getParameter("abrir");
        if (id != null && !id.equals("")){
            ArquivoTemporario arquivo = cadastroService.getArquivoCache(Long.valueOf(id));
            if (arquivo != null){
                httpServletResponse.setContentType(arquivo.getContentType());
                if (abrir == null) {
                    httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + arquivo.getNomeArquivo() +"\"");
                }
                httpServletResponse.setHeader("Pragma", "public");
                httpServletResponse.setHeader("Cache-Control","private, must-revalidate");
                ServletOutputStream ouputStream = httpServletResponse.getOutputStream();
                if (arquivo.getCompactado()) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arquivo.getArquivo());
                    byte[] response = descompactaPrimeiroArquivo(byteArrayInputStream);
                    httpServletResponse.setContentLength(response.length);
                    httpServletResponse.setBufferSize(response.length);
                    ouputStream.write(response);
                } else {
                    httpServletResponse.setContentLength(arquivo.getArquivo().length);
                    httpServletResponse.setBufferSize(arquivo.getTamanho().intValue());
                    ouputStream.write(arquivo.getArquivo(), 0, arquivo.getArquivo().length);
                }
                ouputStream.flush();
                ouputStream.close();
            }
        }
    }

    static public byte[] descompactaPrimeiroArquivo(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        zipInputStream.getNextEntry();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ler(zipInputStream, byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        zipInputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static void ler(InputStream inputStream, OutputStream outputStream) {
        try {
            int count;
            byte[] data = new byte[2048];
            while ((count = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
