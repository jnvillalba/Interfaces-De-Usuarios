  import React, { useState } from 'react'
  import {useNavigate} from 'react-router-dom'
  import Api from '../Api'
  import './LoginRegister.css'

  const Register = () => {

    const navigate = useNavigate()
    const goToLogin = () => navigate('/Login')
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState()
    const [file, setFile] = useState()
    
    const submit = (e) => {
      e.preventDefault()
      
      if (!name) {
        setError("Name is required")
        return
      }

      if (!email) {
        setError("Email is required")
        return
      }

      if (!password) {
        setError("Password is required")
        return
      }
      
      if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)){
        setError("Email is invalid")
        return
      }

      const fetch = async() => {
        try{
            const {data, headers} = await Api.register(email, password, file ? await toBase64(file) : "https://www.pngall.com/wp-content/uploads/12/Avatar-Profile-Vector-No-Background.png", name)
            goToLogin()
        } catch(error){
            console.log(error.message)
            setError(error.message)
        } 
      }
      fetch()
    }

    return(
      <>
        <div className='bodyContainer'>
          <div className='loginContainer'>
            <p className="loginHeading"> Register</p>
            <form onSubmit={submit}>
          
              <div className="loginBox">
                <p>Image</p>
                <label className="loginBtn">
                  <input className="inputFile" type="file" onChange={(e) => setFile(e.target.files[0]) } />
                    Choose an image
                </label>
                
              </div>
              
              <div className="loginBox">
                <p>Name</p>
                <div>
                  <input type="text" value={name} onChange={(e) => setName(e.target.value)}
                    autoComplete="off" placeholder="Enter your name" />
                </div>
              </div>

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
                
              </div>
              {error && (<p style = {{color: "red"}}>{error}</p>)}
              <button type="submit" className="loginBtn">Register</button>
              <div>
                <p className="text"> Have an account?
                <button className="registerBtn" type='button' onClick={goToLogin}> Go to Login </button>
                </p>
              </div>

            </form>
          </div>
        </div>
       </>
    )
  }
  
  const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });

export default Register

 