function expand(objid){
	var obj=document.getElementById(objid);
	if(obj.style.display != "block"){
		obj.style.display="block";
	}else{
		obj.style.display="none";
	}
}

function collapse(objid){
	var obj=document.getElementById(objid);
	if(obj.style.display != "none"){
		obj.style.display="none";
	}else{
		obj.style.display="table";
	}
}

function active_nav(objid){
	elementDisplay(objid);
	var obj=document.getElementById(objid);
	
	if(obj.style.display != "block"){
		this.className = 'active';
	}else{
		this.removeClass('active');
	}
}