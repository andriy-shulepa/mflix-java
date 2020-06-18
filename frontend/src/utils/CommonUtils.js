export function capitalizeFirstLetter(string) {
    return string.replace(/^./, string[0].toUpperCase());
}

export function isNonEmptyArray(array) {
    return array && array.length > 0;
}

