import React from 'react'
import { useEffect, useState } from 'react'
import Api from '../Api'
import { useParams } from 'react-router-dom'
import Review from '../Review'
import { useNavigate } from 'react-router-dom'
import styles from '../Content/Movie.module.css'

export default function UserId() {

    const navigate = useNavigate()
    const goToLogin = () => navigate('/Login')
    const {id} = useParams()
    const [user, setUser] = useState()
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)

    const logOut = () => {
      localStorage.removeItem('token')
      goToLogin()
  }

    useEffect( () => {
        const fetch = async() => {
            try{
                const data = id? await Api.getUserById(id):await Api.getUser()
                console.log("User ID data")
                console.log(data)
                setTimeout(()  => {
                  setLoading(false)
                  setUser(data)
                }, 800)
            } catch(e){
              setError(e.message)
              logOut()
            }
        }
        fetch()
    },[id])


  if (error){
      return <div>{error}</div>
  }

  return (
    
     <div className={styles.ContentTitleProfile}>
            {loading 
                ? <div className={styles.MovieGrid}>LOADING...</div>
                : <>
        <div className="row">
          <div className="col-lg-12">
            <div className="card mb-3">
              <div className="card-body text-center">
                <img src={user.image}  alt={user.id + "avatar"}
                    className="rounded-circle img-fluid" height={100} width={100}
                />
                <h2 className="my-3">{user.name}</h2>
                <p className="text-muted mb-1">Email: {user.email}</p>
              </div>
            </div>
              <section className="container">
              <h5 className="mb-2">My Reviews: {user.reviews.length}</h5>
              <ul>{
                user.reviews.map(review =>
                <Review key={review.id} review={review}/>)
              }</ul>
              </section>
            </div>
          </div>
            </>
            }
            
        </div>
  )
}


