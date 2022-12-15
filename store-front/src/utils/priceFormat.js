export const priceFormat = (number) => {
  if (!number) {
    return ''
  }
  let str = number.toString()
  if (str.length > 2) {
    return str.slice(0, -2) + '.' + str.slice(-2)
  }
  if (str.length === 2) {
    return '0.' + str
  }
  return '0.0' + str
}
