window._gaq = window._gaq || [];

(function() {
  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
  ga.src = 'modulargrid_debug.js';
  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

window.by_vbalanse_vaadin_portal_Analytics = function() {
  this.pushCommand = function(command) {
    _gaq.push(command);
  }
}