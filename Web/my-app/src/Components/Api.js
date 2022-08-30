import Axios from 'axios'

const get = (url) =>
    Axios.get(url, {headers:{authorization:localStorage.getItem('token')}})
        .then(( {data} ) => data)
        .catch((error) => Promise.reject(error.response.data))

const post = (url, data) =>
    Axios.post(url, data, {headers:{authorization:localStorage.getItem('token')}})
        .then(( {data, headers} ) => { return  {data, headers}})
        .catch((error) => Promise.reject(error.response.data))


const getCategories = ()        => get("http://localhost:7070/categories")
const getCategoryById = (id)    => get(`http://localhost:7070/categories/${id}`)
const getLatest = ()            => get("http://localhost:7070/content/latest")
const getTop = ()               => get("http://localhost:7070/content/top")
const getContentById = (id)     => get(`http://localhost:7070/content/${id}`)


const register     = (email, password, image, name)   => post("http://localhost:7070/register",        {email, password, image, name})
const login        = (email, password)                => post("http://localhost:7070/login",           {email, password})
const createReview = (id, text, stars)                => post(`http://localhost:7070/content/${id}`,   {text, stars})

const getUser = () => get(`http://localhost:7070/user`)
//Trae al usuario que definido por el token JWT del header

const getUserById = (id) => get(`http://localhost:7070/user/${id}`)
//Trae al usuario con el id pasado en el parametro

 
const Api = {
    getCategories,
    getCategoryById,
    getTop,
    getLatest,
    getContentById,
    getUserById,
    getUser,
    register,
    login,
    createReview
    
}

export default Api

