export const timeFormat = (time) => {
  return new Date(time).toLocaleString().replace(/\//g, '-')
}
