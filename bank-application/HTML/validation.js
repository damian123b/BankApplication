function isName() {
	//alert("jestem funkcj¹ javaScriptu");
	//var form = $("#form");
	//var clientNameFromForm = $("#name").val();
	var clientNameFromForm = document.forms["form"]["name"].value;
	var nameRegex = /(^[a-z]{2,20})(\s?)([a-z]{0,20})/;
	var checkResult = true;
	if(!clientNameFromForm.match(nameRegex) ||  clientNameFromForm == null || clientNameFromForm=="")
	  {
	  window.alert("Wrong name format or empty name. Try again!");
	  checkResult = false;	
	  return checkResult;
	  }
	      if (checkResult) 
	      {
            alert("Name "+clientNameFromForm+" is in right format. Well done! Sending...");
            return true;
          } 
	      else 
	      {
            return false;
          }
}
