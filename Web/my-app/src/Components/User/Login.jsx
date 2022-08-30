import React, { useState } from 'react'
import {useNavigate} from 'react-router-dom'
import Api from '../Api'
import './LoginRegister.css'


const Login = () => {

  const navigate = useNavigate()
  const goToRegister = () => navigate('/Register')
  const goToHome = () => navigate('/')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState()


  const submit = (e) => {
    e.preventDefault()
    
    const fetch = async() => {   
      try{
          const {data, headers} = await Api.login(email, password)
          console.log("data")
          console.log(data)
          localStorage.setItem('token', headers.authorization)
          goToHome()
      } catch(error){
          console.log("Login data error")
          console.log(error.message)
          setError(error.message)
      } 
    }
    fetch()
  }

  return (
    <>
      <div className='bodyContainer'>
        <div className='loginContainer'>
          <p className="loginHeading"> Login In</p>
          <form onSubmit={submit}>
            <div className="loginBox">
              <p>Email</p>
              <div>
                <input type="text" value={email} onChange={(e) => setEmail(e.target.value)}
                  autoComplete="off" placeholder="Enter your email" />
              </div>
            </div>
            <div className="loginBox">
              <p>Password</p>
              <div>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} autoComplete="off" placeholder="Enter your password" />
              </div>
              {error && (<p style = {{color: "red"}}>{error}</p>)}
            </div> 
            <button type="submit" className="loginBtn">Login</button>
            <div>
              <p className="text">Don't have an account?
              <button className="registerBtn" type='button' onClick={goToRegister}> Go to Register </button>
              </p>
            </div>
          </form>
        </div>
      </div>
    </>
    )
  }

export default Login