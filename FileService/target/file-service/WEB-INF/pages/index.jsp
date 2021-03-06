<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt">
  <head>
    <!-- Basic Page Needs
    ================================================== -->
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>File Service</title>
    <meta name="author" content="inafalcao.com">
    
    <!-- Favicons
    ================================================== -->
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <link rel="apple-touch-icon" href="resources/img/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="resources/img/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="resources/img/apple-touch-icon-114x114.png">

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css"  href="resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="resources/fonts/font-awesome/css/font-awesome.css">

    <!-- Slider
    ================================================== -->
    <link href="resources/css/owl.carousel.css" rel="stylesheet" >
    <link href="resources/css/owl.theme.css" rel="stylesheet">

    <link href="resources/css/jquery.nstSlider.min.css" rel="stylesheet">

    <!-- Stylesheet
    ================================================== -->
    <link rel="stylesheet" type="text/css"  href="resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="resources/css/responsive.css">

    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,700,300,600,800,400' rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="resources/js/modernizr.custom.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <!-- Navigation
    ==========================================-->
    <nav id="tf-menu" class="navbar navbar-default navbar-fixed-top on">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="" href="index.html">
           	Serviço de Arquivos
          </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">

           <!-- <li><a href="#tf-contact" class="page-scroll">Contact</a></li> -->

          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>


    <div id="tf-info" class="text-center" style="background: #fafbfb; padding: 14% 0 14% 0; display:none">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">

                    <div class="section-title center">
                        <h2>Pedido<strong> enviado</strong></h2>
                        <div class="line">
                        </div>
                        <div class="clearfix"></div>          
                    </div>

                    <p class="lead">Em 24 horas, você receberá um e-mail com a confirmação da compra e a cobrança com as opções de pagamento.</p> 
                    <a href="custom.html"class="btn tf-btn btn-default btn-promo">Continuar Personalizando</a>
                </div>
            </div>
        </div>
    </div>



    <!-- Contact Section
    ==========================================-->
    <div id="tf-contact" class="text-center" style="background: #fafbfb; padding-top: 130px;">
        <div class="container">

			<ul>
				<li><a>Adicionar</a></li>
				<li><a>Listar</a></li>
			</ul>

        </div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.1.11.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="resources/js/bootstrap.js"></script>
    
    <script type="text/javascript" src="resources/js/jquery.isotope.js"></script>

    <script src="resources/js/owl.carousel.js"></script>
    <script src="resources/js/bootstrap-lightbox.js"></script>

    <script src="resources/js/jquery.nstSlider.min.js"></script>
    <script src="resources/js/bootstrap-filestyle.min.js" type="text/javascript" > </script>
    
    <script src="resources/js/main.js"></script>

    <!-- Javascripts
    ================================================== -->
    <script language="javascript">
        $('.nstSlider').nstSlider({
            "left_grip_selector": ".leftGrip",
            "value_changed_callback": function(cause, leftValue, rightValue) {
                $(this).parent().find('.leftLabel').text(leftValue);
                $('#cap-image').css({'width' : leftValue});
                
                
            }
        });
        
        $('#basicModal').on('shown.bs.modal', function (e) {

            var $img = $("#cap-image");
            var src = $img.attr("src");

            $('#cap-image-preview').attr('src', src);

            var previeWidth = ($img.width() * 98.267716535)/240 ;
            $('#cap-image-preview').css({'width' : previeWidth});
        });

        // Set PAC as default shipment method.
        $("input:radio[name=shipmentMethod]").filter('[value=PAC]').prop('checked', true);
        
        /*$("#cep").focusout(function() {
          	
          	var cep = $("#cep").val();
          	var ship = $("input:radio[name=shipmentMethod]").val();
        	
          	if(cep && ship) {
          		calculateShipmentFee(cep, ship);

          	}
          	
        });
        
        $("input:radio[name=shipmentMethod]").click(function() {
            var ship = $(this).val();
            var cep = $("#cep").val();
            
            if(ship && cep) {
            	calculateShipmentFee(cep, ship);
            }

        });
        
        var calculateShipmentFee = function(cep, method) {
        	var pathname = window.location.host; 
        	
        	$.ajax({
        	      url: 'http://' + pathname + '/bodegabeer/fee',
        	      data: {
        	         cep: cep,
        	         method: method
        	      },
        	      error: function() {
        	        
        	      },
        	      dataType: 'json',
        	      success: function(data) {
        	    	  console.log(data);
        	         $('#shipValue').text('R$ ' + data);
        	      },
        	      type: 'GET'
        	   });
        };*/

        $("#checkout").click(function () {
            $("#tf-contact").fadeOut(500);
            /* $("#tf-info").fadeIn(2000);
            $("html, body").animate({ scrollTop: 0 }, "slow"); */
    
        });

    </script>

  </body>
</html>