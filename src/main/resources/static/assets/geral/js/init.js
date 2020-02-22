$(function() {

	// Pace.restart();

	var countLastMessages = $("#countLastMessages").data('count');

	$("i.sort").click(function() {
		var elem = $(this);
		var old_value = $("#sort_by").val();
		var new_value = elem.attr('data-option');
		var metodo_atual = $("#sort_method").val();

		if (old_value != new_value) {
			$("#sort_method").val(1);
		} else {
			switch (metodo_atual) {
			case '1':
				$("#sort_method").val(2);
				break;
			case '2':
				$("#sort_method").val(1);
			default:
				break;
			}
		}

		$("#sort_by").val(elem.attr('data-option'));
		$('#page_form').attr({
			'target' : "_self",
			'action' :  $('#page_form').data('list-url')
		}).submit();
	});

	$(".menu_listar_servicos").click(function() {
		window.location.href = deros_context + '/home';
	});

	$(".cel").mask("(99)99999-9999");
	$(".tel").mask("(99)9999-9999");
	$(".datetime_input").mask("99/99/9999 99:99");
	$(".data_input").mask("99/99/9999");
	$(".year_input").mask("9999");

	// $('[data-toggle="tooltip"]').tooltip();
	$('.js-currency').maskMoney({
		decimal : ',',
		thousands : '.',
		allowZero : true
	});

	$(".sidebar-toggle").click(function() {
		if ($("body").hasClass("sidebar-collapse")) {
			createCookie("collapse_sidebar", 0, 10);
		} else {
			createCookie("collapse_sidebar", 1, 10);
		}
	});
	$("#confirmacaoExclusaoModal form").attr("data-url-base", curr_url);

	$('#confirmacaoExclusaoModal').on(
			'show.bs.modal',
			function(e) {
				var button = $(e.relatedTarget);
				var codigo = button.data('codigo');
				var descricao = button.data('descricao');
				var modal = $(this);
				var form = modal.find('form');
				$('#confirmacaoExclusaoModal form #tituloModal').html(
						'Tem certeza que deseja excluir o item: <strong>'
								+ descricao + '</strong>?');

				var action = form.data('url-base');
				if (!action.endsWith('/')) {
					action += '/';
				}
				form.attr('action', action + 'remover/' + codigo);

			});

	$("#filtrar_dados").click(function(e) {
		e.preventDefault();
		formatarCamposFormulario("#page_form");
		$('#page_form').attr({
			'target' : "_self",
			'action' : $('#page_form').data('list-url')
		}).submit();
	});
	
	$("#gerar_relatorio").click(function(e) {
		e.preventDefault();
		var relatorio = '/' + $(this).attr('data-impressao');
		$('#page_form').attr({
			'target' : "_blank",
			'action' : impressao + relatorio
		}).submit();
	});

	$(".marcar_como_lida").click(
			function(e) {
				e.preventDefault();
				e.stopPropagation();
				var item_id = $(this).attr('data-codigo');

				$.get(
						deros_context + "/operador/mensagens/marcarcomolida/"
								+ item_id).done(function(data) {
					if (data == "success") {
						$(".mens_" + item_id).fadeOut();
						countLastMessages = countLastMessages - 1;
						$("#countLastMessages").html(countLastMessages);

					} else {
						alert("Mensagem não pode ser marcada");
					}
					// alert(); }
				});

			});

	function createCookie(name, value, days) {
		var expires;

		if (days) {
			var date = new Date();
			date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
			expires = "; expires=" + date.toGMTString();
		} else {
			expires = "";
		}
		document.cookie = encodeURIComponent(name) + "="
				+ encodeURIComponent(value) + expires + "; path=/";
	}

	function readCookie(name) {
		var nameEQ = encodeURIComponent(name) + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) === ' ')
				c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) === 0)
				return decodeURIComponent(c.substring(nameEQ.length, c.length));
		}
		return null;
	}

	function eraseCookie(name) {
		createCookie(name, "", -1);
	}

	function dump(obj) {
		var out = '';
		for ( var i in obj) {
			out += i + ": " + obj[i] + "\n";
		}

		alert(out);

		// or, if you wanted to avoid alerts...

		var pre = document.createElement('pre');
		pre.innerHTML = out;
		document.body.appendChild(pre)
	}

	function stripHTML(dirtyString) {
		return $(dirtyString).text()
	}

	function scrollToAnchor() {
		elem = $("#model_section").val();

		if (elem != null && elem != "") {
			var element = document.getElementById(elem);
			console.log(element);
			element.scrollIntoView();

		}

	}
	function changeClass(element, newClass, oldClass) {
		if ($(element).hasClass(oldClass)) {
			$(element).removeClass(oldClass);
		}
		if (!$(element).hasClass(newClass)) {
			$(element).addClass(newClass);
		}

	}

	function validateForm(myForm) {
		var validator = $(myForm).data("bs.validator");

		validator.validate();
	}

	function invalidateForm(myForm) {
		var validator = $(myForm).data("bs.validator");
		$(myForm).validator("destroy");
	}

	function novaJanela(pagina, nome, w, h, scroll) {
		var win = null;
		LeftPosition = (screen.width) ? (screen.width - w) / 2 : 0;
		TopPosition = (screen.height) ? (screen.height - h) / 2 : 0;
		settings = 'height=' + h + ',width=' + w + ',top=' + TopPosition
				+ ',left=' + LeftPosition + ',scrollbars=' + scroll
				+ ',resizable'
		win = window.open(pagina, nome, settings);
	}

	function scrollIntoView(elem) {
		var elemento = document.getElementById(elem);
		elemento.scrollIntoView(true);
	}

	function ativacaoItem(check, tipo) {

		var funcao = "";
		var item_id = $(check).attr('data-codigo');
		var item_desc = $(check).attr('data-descricao');

		var checked = ($(check).attr('checked') == "checked" ? true : false);

		if (checked) {
			funcao = "d";
		} else {
			funcao = "a";
		}

		var r = confirm((funcao == "d" ? "Desativar" : "Ativar") + " item ("
				+ item_desc + ")?");
		if (r == true) {
			$("#overlay-list").css('display', "block");
			// var url = deros_context
			// + "/menu/rest/comandos/ativacaosupervisor/"
			// + item_id + "?c=" + funcao;
			$.get(
					deros_context + "/menu/rest/comandos/ativacao/" + item_id
							+ "?c=" + funcao + "&tipo=" + tipo).done(
					function(data) {
						if (data == "success") {
							$(check).prop('checked', !checked);
							$(check).attr('checked', !checked);
							changeClass("#message_table", "callout-success",
									"callout-danger");
							$("#message_table_text")
									.html(
											(funcao == "d" ? "Desativação"
													: "Ativação")
													+ " bem sucedida");

							$("#message_table").show(1000).delay(2500).queue(
									function(n) {
										$(this).hide(1000);
										n();
									});

						} else {
							changeClass("#message_table", "callout-danger",
									"callout-success");
							$("#message_table_text").html(data);
							$("#message_table").show().delay(4000).queue(
									function(n) {
										$(this).hide(1000);
										n();
									});
						}
						// alert(); }
					}).always(function() {
				$("#overlay-list").css('display', "none");
			});

		}

	}

});
