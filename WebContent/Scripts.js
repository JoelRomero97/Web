//Script para el carrusel de imágenes (LoginForm y AdminNavigation)
var myIndex = 0;
function carousel()
{
   var imagenes = document.getElementsByClassName("slideshow");
   for (i = 0; i < imagenes.length; i++)
      imagenes[i].style.display = "none";
   myIndex ++;
   if (myIndex > imagenes.length)
      myIndex = 1
   imagenes[myIndex-1].style.display = "block";
   setTimeout(carousel, 3000);
}

function showModal(document)
{
	document.getElementById('modal').style.display='block'
}

function closeModal(document)
{
	document.getElementById('modal').style.display='none'
    window.location.href = 'AddUserForm.html';
}

function clickOutside()
{
	window.onclick = function modalFunction(event)
	{
	    if (event.target == document.getElementById('modal'))
	        closeModal(document);
	}
}

function closeModalEdit(document, ruta)
{
	document.getElementById('modal').style.display='none'
    window.location.href = ruta;
}

function clickOutsideEdit(ruta)
{
	window.onclick = function modalFunction(event)
	{
	    if (event.target == document.getElementById('modal'))
	        closeModalEdit(document, ruta);
	}
}

