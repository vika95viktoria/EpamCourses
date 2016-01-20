<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link type="text/css" rel="stylesheet" href="style/bootstrap.min.css" />
<html><head><title>XML Parsing</title></head>
<body>
<form class="form-horizontal well" name="Simple" action="parser" method="POST" style="margin-left: 20%;margin-top: 10%;width: 60%">
  <fieldset>
    <legend>Choose parser</legend>
    <div class="form-group" style="margin-left: 5%">
      <input type="radio" name="radios" onclick = \"getAnswer('1')" value="1" checked="checked"> DOM<Br>
      <input type="radio" name="radios" onclick = \"getAnswer('2')" value="2"> SAX<Br>
      <input type="radio" name="radios" onclick = \"getAnswer('3')" value="3"> StAX<Br>
    </div>
    <div class="form-group" style="margin-left: 0%">
      <input class="btn btn-primary" type="submit"  name="button"  value="Parse"/>
    </div>
  </fieldset>
  <fieldset>
    <legend>Marshalling</legend>
    <div class="form-group" style="margin-left: 0%">
      <input class="btn btn-primary" type="submit"  name="button"  value="Marshall"/>
    </div>
  </fieldset>
</form>
</body></html>