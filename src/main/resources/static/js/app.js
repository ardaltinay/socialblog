$("#bio-save").click(function () {
    let bio = $("#bio-textarea").val();
    $.post("http://localhost:8080/settings", bio, function () {
        alert("Biography: " + bio);
    })
});