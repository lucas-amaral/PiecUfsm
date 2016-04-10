package br.ufsm.inf.controller;

import br.ufsm.inf.model.*;
import br.ufsm.inf.service.CadastroService;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
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

    @RequestMapping("/gerar-pdf.htm")
    public void gerar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws SQLException, ClassNotFoundException, JRException, DocumentException, IOException {
        Usuario usuarioSecao = (Usuario) httpServletRequest.getSession().getAttribute("usuarioLogado");
        Piec piec = cadastroService.getPiec(Long.valueOf(httpServletRequest.getParameter("idPiec")));
        if (piec != null && piec.getId() != null) {
            if (usuarioSecao.getTipo().equals(Usuario.TIPO_COLEGIADO) || piec.getId().equals(usuarioSecao.getPiec().getId())) {
                Map<String, Object> parametro = new HashMap<String, Object>();
                parametro.put("idPiec", piec.getId());
                parametro.put("ufsm_logo", httpServletRequest.getSession().getServletContext().getRealPath("/") + "/resources/img/ufsm_logo.png");
                parametro.put("inf_logo", httpServletRequest.getSession().getServletContext().getRealPath("/") + "/resources/img/inf_logo.png");
                Arquivo arquivoJasper = cadastroService.arquivoJasper();
                JasperReport report = JasperCompileManager.compileReport(arquivoJasper.getArquivo().getBinaryStream());
                JasperPrint print = JasperFillManager.fillReport(report, parametro, cadastroService.getDao().getConnection());
                httpServletRequest.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);
                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.setHeader("Content-Disposition","inline; filename=\"piec_"+ piec.getAluno().getMatricula() +".pdf\"");
                JRPdfExporter pdfExporter = new JRPdfExporter();
        //        pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        //        pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        //        pdfExporter.exportReport();
                List<InputStream> pdfs = new ArrayList<InputStream>();
                httpServletResponse.setHeader("Pragma", "public");
                httpServletResponse.setHeader("Cache-Control","private, must-revalidate");
                ServletOutputStream ouputStream = httpServletResponse.getOutputStream();

                File pdfJasper = new File("piec_"+ piec.getAluno().getMatricula() +".pdf");
                pdfJasper.createNewFile();
                FileOutputStream arquivo = new FileOutputStream(pdfJasper);
                JasperExportManager.exportReportToPdfStream(print, arquivo);
                pdfs.add(new FileInputStream(pdfJasper));
                for (Arquivo pdf : piec.getDocumentos()) {
                    pdfs.add(pdf.getArquivo().getBinaryStream());
                }
                for (PiecDisciplina piecDisciplina : piec.getPiecDisciplinas()) {
                    if (piecDisciplina.getPlanoEnsino() != null && piecDisciplina.getPlanoEnsino().getArquivo() != null) {
                        pdfs.add(piecDisciplina.getPlanoEnsino().getArquivo().getBinaryStream());
                    }
                }
                try {
                    juntarPdfs(pdfs, ouputStream, true);
                } catch (com.lowagie.text.DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void geraPdfUnico(List<InputStream> pdfs, String nomeArquivo) {
        try {
            OutputStream out = new FileOutputStream(new File(nomeArquivo + ".pdf"));
            juntarPdfs(pdfs, out, true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void juntarPdfs(List<InputStream> documentos, OutputStream outputStream, boolean paginate) throws DocumentException, IOException, com.lowagie.text.DocumentException {
        Document document = new Document();
        try {
            List<InputStream> pdfs = documentos;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Página " + currentPageNumber + " de " + totalPages, 520,5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
