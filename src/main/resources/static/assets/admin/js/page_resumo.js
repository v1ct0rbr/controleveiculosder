$(document).ready(

		function() {

			setTimeout(function() {
				window.location.reload(1);
			}, 180000); // 3 minutos

			var curr_year = $("#year").text();

			var url_setores = deros_context
					+ '/comum/resumo/topincidentes/setores';
			var url_tipoServico = deros_context
					+ '/comum/resumo/topincidentes/tiposervico';
			var url_problema = deros_context
					+ '/comum/resumo/topincidentes/problema';
			var url_servicos_por_mes = deros_context
					+ '/comum/resumo/topincidentes/quantservicos';

			var common_chart_options = {
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}

			chartColors = [ window.chartColors.purple,
					window.chartColors.orange, window.chartColors.yellow,
					window.chartColors.green, window.chartColors.red,
					window.chartColors.blue, window.chartColors.grey ]

			carregaDados(url_setores, $("#dias_passados").val(), $("#myChart"),
					'', false, 'pie');

			carregaDados(url_tipoServico, $("#dias_passados").val(),
					$("#chart_tiposervico"), 'Nº de incidentes', false, 'pie');

			carregaDados(url_problema, $("#dias_passados").val(),
					$("#chart_problema"), 'Nº de incidentes',
					false, 'pie');
		
			carregaDadosServicosPorMes();

			function adddata() {
				myLineChart.data.datasets[0].data[7] = 60;
				myLineChart.data.labels[7] = "Newly Added";
				myLineChart.update();
			}
		});

function carregaDados(url, param_dias, myChart, title, options, tipo) {
	var dados = {
		labels : [],
		datasets : [ {
			label : title,
			data : [],
			backgroundColor : [],
		} ]

	};
	$.get(url, {
		dias_passados : param_dias
	}).done(
			function(dataObj) {
				if (dataObj) {
					try {
						var i = 0;
						$.map(dataObj, function(item) {
							dados.labels.push(item.nome);
							dados.datasets[0].data.push(item.incidentes);
							dados.datasets[0].backgroundColor
									.push(window.chartColors[i]);
							i++;
						});

						myChart = new Chart(myChart, {
							type : tipo,
							data : dados,
							options : (options != null ? options : [])
						});

					} catch (e) {
						console.log(e);
						return false;
					}
				}
			});

}

//function carregaDadosArray(url, param_dias) {
//	var dados = {
//		labels : [],
//		datasets : [ {
//			label : '',
//			data : [],
//			backgroundColor : [],
//		} ]
//
//	};
//	$.get(url, {
//		dias_passados : param_dias
//	}).done(
//			function(dataObj) {
//				if (dataObj) {
//					try {
//						var i = 0;
//						$.map(dataObj, function(item) {
//							dados.labels.push(item.nome);
//							dados.datasets[0].data.push(item.incidentes);
//							dados.datasets[0].backgroundColor
//									.push(window.chartColors[i]);
//							i++;
//						});
//
//					} catch (e) {
//						console.log(e);
//						return false;
//					}
//				}
//			});
//	return dados;
//}

function carregaDadosServicosPorMes() {

	var Months2 = [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ];

	var presets = window.chartColors;
	var utils = Samples.utils;

	var config = {
		type : 'line',
		data : {
			labels : Months2,
			datasets : [ {
				label : 'Ano: ' + ano2,
				backgroundColor : utils.transparentize(window.chartColors[1]),
				borderColor : window.chartColors[1],
				data : servicos_ano_atual,
				fill : true,
			}, {
				label : 'Ano:  ' + ano1,
				fill : true,
				backgroundColor : utils.transparentize(window.chartColors[5]),
				borderColor : window.chartColors[5],
				data : servicos_ano_passado,
			} ]
		},
		options : {
			responsive : true,
			title : {
				display : true,
				text : 'Comparativo de serviços realizados'
			},
			tooltips : {
				mode : 'index',
				intersect : false,
			},
			hover : {
				mode : 'nearest',
				intersect : true
			},
			scales : {
				xAxes : [ {
					display : true,
					scaleLabel : {
						display : true,
						labelString : 'Mês'
					}
				} ],
				yAxes : [ {
					display : true,
					scaleLabel : {
						display : true,
						labelString : 'Quantidade'
					}
				} ]
			}
		}
	};

	var ctx = document.getElementById('servicosPorMes').getContext('2d');
	window.myLine = new Chart(ctx, config);

}
