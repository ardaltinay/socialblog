// email validation function
function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return true;
}

let currentEmail = $("#email-input").val();
// email save button click function
$("#email-save").click(function () {
    // hide info on every click
    $(".notValid-email").hide();
    $(".success-email").hide();
    $(".fail-email").hide();
    // control current email and writing email equality
    let email = $("#email-input").val();
    if (currentEmail == email) {
        return false;
    }
    // check email validation
    if (!validateEmail(email)) {
        $(".notValid-email").show();
    } else {
        // jquery post request
        $.post("http://localhost:8080/settings/ajax?type=email",  { email: email }, function () {
        }).done(function( data ) {
            if (data.StatusCode == 1) {
                $(".success-email").show();
            } else {
                $(".fail-email").show();
            }
        });
    }
});

// password save button click function
$("#pass-save").click(function () {
    // hide info on every click
    $(".notValid-pass").hide();
    $(".success-pass").hide();
    $(".fail-pass").hide();
    // get values from html form
    let current_pass = $("#current-pass").val();
    let new_pass = $("#new-pass").val();
    let repeat_pass = $("#new-pass-valid").val();
    // check new password and repeat password equality
    if (new_pass != repeat_pass) {
        $(".notValid-pass").show();
        return false;
    }
    // jquery post request
    $.post("http://localhost:8080/settings/ajax?type=pass",
        { currentPassword: current_pass, newPassword: new_pass, repeatPassword: repeat_pass }, function () {
    }).done(function( data ) {
        if (data.StatusCode == 1) {
            $(".success-pass").show();
        } else {
            $(".fail-pass").show();
        }
    });
});

let currentBio = $("#bio-textarea").val();
// biography save button click function
$("#bio-save").click(function () {
    // hide info on every click
    $(".success-bio").hide();
    $(".fail-bio").hide();
    // control current bio and writing bio equality
    let bio = $("#bio-textarea").val();
    if (currentBio == bio) {
        return false;
    }
    // jquery post request
    $.post("http://localhost:8080/settings/ajax?type=bio",  { biography: bio }, function () {
    }).done(function( data ) {
        if (data.StatusCode == 1) {
            $(".success-bio").show();
        } else {
            $(".fail-bio").show();
        }
    });
});


