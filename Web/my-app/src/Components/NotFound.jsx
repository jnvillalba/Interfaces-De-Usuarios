import React from 'react'
import {useNavigate} from 'react-router-dom'

const NotFound = () => {
    const navigate = useNavigate()
    const goToHome = () => navigate('/')
    return(
      <div>
        <h1> Page not found </h1>
        <button type='button' onClick={goToHome}> Go to Home</button>
      </div>
    )
  }

export default NotFound

