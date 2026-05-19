// Minimal client-side validation helpers
function required(id) {
    var el = document.getElementById(id);
    return el && el.value.trim().length > 0;
}
