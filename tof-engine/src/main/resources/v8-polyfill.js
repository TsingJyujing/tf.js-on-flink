/**
 * polyfill of console
 * @type {{log: *, error: *}}
 */
var console = {
    "log": stdout_print,
    "error": stderr_print
};