<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Compose ur mail</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<body >
	<div id="sample">
		<script type="text/javascript"
			src="http://js.nicedit.com/nicEdit-latest.js"></script>
		<script type="text/javascript">
//<![CDATA[
        bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  //]]>
  </script>


<div class="container" ">
		<form:form autocomplete="off" action="uploadDetails" method="POST" modelAttribute="mail" enctype="multipart/form-data">
		
		<div class="form-group">
		<label class="col-lg-2 control-label">
                                                  Name
                                                  </label>
                                                  <div class="col-lg-10">
                                                      <form:input type="email" path="userEmail" class="form-control" required="required"/>
                                                  </div>
                                              </div>
		
		
		<div class="form-group">
		<label class="col-lg-2 control-label">
                                                 Password
                                                 </label>
                                                  <div class="col-lg-10">
                                                      <form:password path="password" class="form-control" required="required"/>
                                                  </div>
                                              </div>
                                              
		<div class="form-group">
                                                  <label class="col-lg-2 control-label">Subject</label>
                                                  <div class="col-lg-10">
                                                      <form:input path="subject" class="form-control" required="required"/>
                                                  </div>
                                              </div>
		
			<div class="form-group">
			<label class="col-lg-2 control-label" path="comment">
		        Message
		        </label>
				<div class="col-lg-10">
					<form:textarea rows="10" cols="30" class="form-control" path="comment" />
				</div>
			</div>
			
			<div class="form-group">
			<label class="col-lg-2 control-label" id="receiver">Receiver List</label>
					<form:input type="file" path="receiver" name="receiver" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
					</div>

			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<span class="btn green fileinput-button"> <i
						class="fa fa-plus fa fa-white"></i> <span>Attachment Path</span> 
						<form:input type="file" path="attachFile" multiple="multiple" name="attachFile" class="btn btn-info"/>
					</span>
					</div>
					</div>
					
					
						<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<button class="btn btn-send btn-success" type="submit">Send</button>
				</div>
			</div>
		</form:form>
		</div>
</body>

</html>