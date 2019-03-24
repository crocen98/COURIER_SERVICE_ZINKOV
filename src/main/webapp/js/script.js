function hashPass(setInput,formId, passFieldId, loginFieldId) {
    document.getElementById(formId).addEventListener("submit", function () {
        var pass = document.getElementById(passFieldId);
        var sold = document.getElementById(loginFieldId);
        document.getElementById(setInput).value = sha256(pass.value + sold.value);
    })
}