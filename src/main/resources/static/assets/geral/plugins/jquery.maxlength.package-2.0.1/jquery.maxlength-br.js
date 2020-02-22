/* http://keith-wood.name/maxlength.html
   French initialisation for the jQuery Max Length extension
   Written by Keith Wood (kbwood{at}iinet.com.au) April 2012. */
(function($) { // hide the namespace

$.maxlength.regionalOptions['br'] = {
	feedbackText: '{r} caracteres restantes (Máximo de {m})',
	overflowText: '{o} caracteres a mais (Máximo de {m})'
};
$.maxlength.setDefaults($.maxlength.regionalOptions['br']);

})(jQuery);
