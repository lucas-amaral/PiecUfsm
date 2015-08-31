// Depois da página carregada
$(document).ready(function() {
    
	// Abre Abre
	$(".abre-abre").click(function () {
		if (jQuery(this).next().is(":hidden")) {
			jQuery(this).next().slideDown("fast");
			jQuery(this).children('span').html('-');			
			return false;
		} else {
			jQuery(this).next().slideUp("fast");
			jQuery(this).children('span').html('+');			
			return false;
		}
	});	

	// Colorbox	
	$("a.colorbox-inline").colorbox({
		inline:true, href:"#bloco-inline"
	});
	
	// Colorbox
	$("a.colorbox").colorbox({
		//rel: 'galeria',
		current: '{current} de {total}',
		previous: 'Anterior',
		next: 'Próxima',
		close: 'Fechar',
		xhrError: 'Este conteúdo apresentou uma falha em seu carregamento!',
		imgError: 'Esta imagem apresentou um erro!',
		maxWidth: '95%',
		maxHeight: '95%'
		});
	
		// Redimensionador
		var resizeTimer;
		function resizeColorBox()
		{
			if (resizeTimer) clearTimeout(resizeTimer);
			resizeTimer = setTimeout(function() {
					if (jQuery('#cboxOverlay').is(':visible')) {
							jQuery.colorbox.load(true);
					}
			}, 300);
		}
	
	// Redimensiona quando muda a orientação
	$(window).resize(resizeColorBox);
	window.addEventListener("orientationchange", resizeColorBox, false);	  
	  
	
});