// my menu button for phones - opens the menu if it's closed and closes it if it's open
function toggleMenu() {
    var nav = document.getElementById("nav");
    if (nav.className == "nav") {
        nav.className = "nav open";
    } else {
        nav.className = "nav";
    }
}
