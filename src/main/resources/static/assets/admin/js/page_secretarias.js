$(document).ready(
		function() {
			$('.telramal').inputFilter(function(value) {
				return /^\d*$/.test(value);
			});
			$("#prefix_tel").on('keyup', function(e) {

				if ($(this).val().length >= 4 && notInSpecialTypes(e.keyCode))
					$("#ramal_principal").focus();

			});
			$("#ramal_principal").on('keyup', function(e) {
				pos = $(this).caret();
				if ((e.keyCode == 37 || e.keyCode == 8) && pos == 0) {
					$("#prefix_tel").focus();
				}
			});

			function notInSpecialTypes(value) {
				if (value != 38 && value != 37 && value != 40 && value != 8
						&& value != 9) {
					return true;
				} else
					return false;
			}

		});