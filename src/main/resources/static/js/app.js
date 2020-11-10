$("#bio-save").click(function () {
    let bio = $("#bio-textarea").val();
    $.post("http://localhost:8080/settings/ajax?type=bio",  { biography: bio }, function () {
    }).done(function( data ) {
        if (data.StatusCode == 1) {
            alert("Biograpy is updated.");
        } else {
            alert("Biograpy is not updated. Please try again later.");
        }
    });
});

$("#email-save").click(function () {
    let email = $("#email-input").val();
    $.post("http://localhost:8080/settings/ajax?type=email",  { email: email }, function () {
    }).done(function( data ) {
        if (data.StatusCode == 1) {
            alert("Email is updated.");
        } else {
            alert("Email is not updated. Please try again later.");
        }
    });
});


