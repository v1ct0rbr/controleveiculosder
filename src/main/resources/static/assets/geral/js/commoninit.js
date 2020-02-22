// Restricts input for each element in the set of matched elements to the given inputFilter.
(function($) {
	$.fn.inputFilter = function(inputFilter) {
		return this
				.on(
						"input keydown keyup mousedown mouseup select contextmenu drop",
						function() {
							if (inputFilter(this.value)) {
								this.oldValue = this.value;
								this.oldSelectionStart = this.selectionStart;
								this.oldSelectionEnd = this.selectionEnd;
							} else if (this.hasOwnProperty("oldValue")) {
								this.value = this.oldValue;
								this.setSelectionRange(this.oldSelectionStart,
										this.oldSelectionEnd);
							}
						});
	};
}(jQuery));

$(function() {
	$('[data-toggle="tooltip"]').tooltip();

});

function keyUpToUpper(element) {

	$(element).bind('keyup', function(e) {
		if (e.which >= 97 && e.which <= 122) {
			var newKey = e.which - 32;
			// I have tried setting those
			e.keyCode = newKey;
			e.charCode = newKey;
		}

		$(element).val(($(element).val()).toUpperCase());
	});

}

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

function verificarMensagens() {
	var messagesCookie = readCookie("mensagens");
	if (mensagens == null) {

	}
}

function formatTimestamp(unixTimeStamp) {
	var data = new Date(unixTimeStamp);
	var formattedDate = data.toLocaleString();
	return formattedDate;

}

function novaJanela(pagina, nome, w, h, scroll) {
	var win = null;
	LeftPosition = (screen.width) ? (screen.width - w) / 2 : 0;
	TopPosition = (screen.height) ? (screen.height - h) / 2 : 0;
	settings = 'height=' + h + ',width=' + w + ',top=' + TopPosition + ',left='
			+ LeftPosition + ',scrollbars=' + scroll + ',resizable'
	win = window.open(pagina, nome, settings);
}

function createDatatable(elem, pageLength, orderColumn, orderMethod) {
	$(elem).DataTable(
			{
				"language" : {
					"url" : deros_context
							+ "/assets/geral/locale/Portuguese-Brasil.json"
				},
				"paging" : true,
				"pageLength" : pageLength,
				"lengthChange" : false,
				"searching" : true,
				"info" : true,
				"ordering" : true,
				"order" : [ [ orderColumn, orderMethod ] ],
				"autoWidth" : true
			});
}

function formatRepo(repo) {

	var markup = "";
	if (repo.text) {
		markup += "<span>" + repo.text + "</span>";
	}
	return markup;
}

function formatRepoSelection(repo) {
	return repo.text;
}

function clearForm() {
	$("#clear_form").click(function() {
		window.location.replace(curr_url + "/novo");
	});
}

function recarregarSetores(selectbox, elem_id, overlay) {
	$(overlay).css('display', "block");
	$.get(deros_context + "/comum/utils/setores")

			.done(
					function(dataObj) {
						$(selectbox).html("");
						if (dataObj) {
							$(selectbox).append(
									'<option value="0">Selecione</option>');
							try {
								var i = 0;
								$.map(dataObj, function(item) {
									$(selectbox).append(
											'<option value="' + item.id + '">'
													+ item.nome + '</option>');
								});
							} catch (e) {
								console.log(e);
								return false;
							} finally {

								if ($.trim(elem_id).length > 0) {
									$(selectbox).val(elem_id);

								}
								$(overlay).css('display', "none");
							}
						}

					});
}

function validateForm(myForm) {
	var validator = $(myForm).data("bs.validator");

	validator.validate();
}

function invalidateForm(myForm) {
	var validator = $(myForm).data("bs.validator");
	$(myForm).validator("destroy");
}

// popover: {
// image: [
// ['imagesize', ['imageSize100', 'imageSize50', 'imageSize25']],
// ['float', ['floatLeft', 'floatRight', 'floatNone']],
// ['remove', ['removeMedia']]
// ],
// link: [
// ['link', ['linkDialogShow', 'unlink']]
// ],
// air: [
// ['color', ['color']],
// ['font', ['bold', 'underline', 'clear']],
// ['para', ['ul', 'paragraph']],
// ['table', ['table']],
// ['insert', ['link', 'picture']]
// ]
// },

// ['style', ['bold', 'italic', 'underline', 'clear']],
// ['font', ['strikethrough', 'superscript', 'subscript']],
// ['fontsize', ['fontsize']],
// ['color', ['color']],
// ['para', ['ul', 'ol', 'paragraph']],
// ['height', ['height']],
// ['insert', ['link']]

/**
 * 
 * @param url
 *            (url da pesquisa)
 * @param selectbox
 *            (combobox que é alimentacao)
 * @param elem_id
 *            (id que contém a identificação atual do setor)
 * @param hasFirstItem
 *            (verificação para append de item nulo = <option
 *            value="0">Selecione</option>)
 * @param overlay
 *            (identificação do elemento da página que corresponde ao loading)
 * @returns
 */
function recarregarItensCombo(url, selectbox, elem_id, hasFirstItem, overlay,
		title) {

	$(overlay).css('display', "block");
	$.get(deros_context + url)

	.done(
			function(dataObj) {
				$(selectbox).html("");
				if (dataObj) {
					if (hasFirstItem) {
						$(selectbox).append(
								'<option value="">'
										+ (title != null ? title : 'Selecione')
										+ '</option>');
					}
					try {
						var i = 0;
						$.map(dataObj, function(item) {
							$(selectbox).append(
									'<option value="' + item.id + '">'
											+ item.descricao + '</option>');
						});
					} catch (e) {
						console.log(e);
						return false;
					} finally {

						if ($.trim(elem_id).length > 0 && elem_id > 0) {
							$(selectbox).val(elem_id);

						} else {
							$(selectbox).val(0);
						}
						$(overlay).css('display', "none");

					}

				}

			});
}

function formatarCamposFormulario(formPage) {
	$(
			formPage + " input[type=text], " + formPage + " input[type=email],"
					+ formPage + " input[type=password]," + formPage
					+ " input[type=number]," + formPage + " textarea").each(
			function(index, value) {
				$(this).val($.trim($(this).val()));
			});
}

function submeterFormulario(formPage, message, args) {

	formatarCamposFormulario(formPage);
	console.log('campos formatados')
	$(formPage).validator();
	var validator = $(formPage).data("bs.validator");
	validator.validate();

	if (!validator.hasErrors()) {
		console.log('campos válidos')
		bootbox.confirm({
			size : "small",
			message : message,
			buttons : {
				confirm : {
					label : 'Sim',
					className : 'btn-success submitable'
				},
				cancel : {
					label : 'Não',
					className : 'btn-danger'
				}
			},
			callback : function(result) {

				if (result) {
					console.log('submetendo formulário');
					if (typeof args !== 'undefined') {
						$.map(args, function(value, index) {

							$(formPage).append(
									$("<input>").attr("type", "hidden").attr(
											"name", index).val(value));
						});
					}

					document.getElementById(formPage.substr(1)).submit();
					console.log('formulário submetido');
				}

			}
		})

	} else {
		console.log('campos invalidos')
	}

}