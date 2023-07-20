// cookies.js

// Function to set cookie value
function setCookie(cname, cvalue, exdays) {
	const d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	let expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

// Function to retrieve cookie value
function getCookie(cname) {
	let name = cname + "=";
	let decodedCookie = decodeURIComponent(document.cookie);
	let ca = decodedCookie.split(';');
	for (let i = 0; i < ca.length; i++) {
		let c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

// Get the user object from the JSP code
let userStr = '<%= userStr %>';
console.log("User object: ", userStr);

// Parse the user object as JSON
let user = JSON.parse(userStr);

// Set the cookies with the user details
setCookie("userId", user.id);
setCookie("userName", user.name);
setCookie("email", user.email);

// Retrieve the cookies and display the user details
let userId = getCookie("userId");
let userName = getCookie("userName");
let email = getCookie("email");

console.log("User ID:", userId);
console.log("Username:", userName);
console.log("Email:", email);

// Display the user details
document.write("User ID: " + userId + "<br>");
document.write("Username: " + userName + "<br>");
document.write("Email: " + email + "<br>");