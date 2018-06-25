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

function closeModal(ruta)
{
	document.getElementById('modal').style.display='none'
    window.location.href = ruta;
}

function clickOutside(ruta)
{
	window.onclick = function modalFunction(event)
	{
	    if (event.target == document.getElementById('modal'))
	        closeModal(ruta);
	}
}

function allowDrop(ev)
{
    ev.preventDefault();
}

function drag(ev)
{
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev)
{
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
}

