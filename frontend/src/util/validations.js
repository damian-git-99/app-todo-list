/**
 * It returns true if the field is empty, otherwise it returns false.
 * @param [field] - The field to be validated.
 * @returns A function that takes a parameter and returns a boolean.
 */
export const isEmpty = (field) => {
  const fieldWithoutWhiteSpaces = field.trim();
  if (fieldWithoutWhiteSpaces === '') return true;
  return false;
};

/**
 * It checks if there's an empty field in the fields array.
 * @param fields - [
 * @returns The function isThereAnEmptyField is being returned.
 */
export const isThereAnEmptyField = (...fields) => {
  console.log(fields);
  for (let i = 0; i < fields.length; i++) {
    if (isEmpty(fields[i])) return true;
  }
  return false;
};
