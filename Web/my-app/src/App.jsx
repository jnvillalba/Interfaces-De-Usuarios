import React from 'react'
import {BrowserRouter, Routes, Route}from 'react-router-dom'
import User from './Components/User/User'
import Home from './Components/Home'
import Login from './Components/User/Login'
import Register from './Components/User/Register'
import LatestContentRoute from './Components/Content/LatestContentRoute'
import TopContentRoute from './Components/Content/TopContentRoute'
import NotFound from './Components/NotFound'
import './App.css'
import CategoriesRoute from './Components/Category/CategoriesRoute'
import CategoryPage from './Components/Category/CategoryPage'
import MoviePage from './Components/Content/MoviePage'
import NavBar from './Components/NavBar'

function App() {
  return (
  <>
    <BrowserRouter>
      <NavBar/>
      <Routes>    
        <Route index element={<Home/>} />
        <Route exact path='/Login' element={<Login />} />
        <Route exact path='/Register' element={<Register />} />
        <Route exact path='/User' element={<User />} />
        <Route exact path='/User/:id' element={<User />} />
        <Route exact path='/Content/Latest' element={<LatestContentRoute />} />
        <Route exact path='/Content/Top' element={<TopContentRoute />} />
        <Route exact path='/Content/:id' element={<MoviePage />} />
        <Route exact path='/Categories' element={<CategoriesRoute />} /> 
        <Route exact path='/Categories/:id' element={<CategoryPage />} />
        <Route exact path='*' element={<NotFound/>}/>          
      </Routes>
    </BrowserRouter>
  </>
  )
}

export default App