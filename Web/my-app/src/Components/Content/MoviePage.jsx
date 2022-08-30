import React from 'react'
import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Api from '../Api'
import styles from '../Content/Movie.module.css'
import Review from '../Review'
import './MoviePage.css'
import jwt_decode from "jwt-decode";


export default function MoviePage() {
    const {id} = useParams()
    const [movieContent, setMovieContent] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)
    const [text, setText] = useState('') 
    const [stars, setStars] = useState(0)    

    const fetch = async() => {
        try{
            const data = await Api.getContentById(id)
            console.log("data")
            setMovieContent(data)
            setLoading(false)
            console.log("movieContent")
            console.log(movieContent)
        } catch(e){
            setLoading(false)
            setError('unexpected error fetching movie')
        } 
    }

    useEffect( () => {
        fetch()
    },[])

    const submit = (e) => {
        const createReview = async() => {
          try{
             
              const {data} = await Api.createReview(id, text, stars)
              console.log("data")
              console.log(data)
              fetch()
              
          } catch(e){
              console.log(e)
              setError(e.message)
          } 
      }
      createReview()
        e.preventDefault()
  
      }

      if (loading){
        return <div>loading...</div>
    }


    let myReview 
    if (localStorage.getItem('token')){
        const decoded = jwt_decode(localStorage.getItem('token'))
        const foundMyReview = movieContent.reviews.find(review => review.user.id === decoded.id)
        if (foundMyReview){
          myReview = ( 
            <div className='container' >
            <h2> My Review: </h2>
            <Review review={foundMyReview}/>
            </div>)
        } else {
            myReview = (
                <div className='container' >
                    <form onSubmit={submit}>
                        <div> 
                            <h2> Make a review of the movie here: </h2>
                            <textarea style={{width: "100%", maxWidth: "100%"}} value={text} onChange={(e) => setText(e.target.value)} />
                        </div>
                        <div>
                            <h2> Score: </h2>
                            <input type="number" min={0} max={5} value={stars} onChange={(e) => setStars(e.target.value)} />
                        </div>
                        {error && (<p style = {{color: "red"}}>{error}</p>)}
                        <input type="submit" value="Submit" />
                    </form>
                </div>
            ) 
        }
    }

  return (
     <>
        <div>
            <div className="container mt-5 mb-5">
                <div className="card">	
                    <div className="row g-0">
                        <div className="col-md-4 border-end">
                            <div className="d-flex flex-column justify-content-center">
                                <div className="main_image">
                                    <img className={styles.MovieImg} src={movieContent.poster} 
                                        alt={movieContent.title}/>	
                                </div>	 
                            </div>
                        </div>
                        <div className="col-md-8">
                            <div className="p-3 right-side">
                                <div className="d-flex justify-content-between align-items-center">
                                    <h3>{movieContent.title}</h3>	
                                </div>	
                                <div className="mt-2 pr-3 content">	
                                    <h4>Description:</h4>
                                    <p>{movieContent.description}</p>	
                                </div>
                                <div className="d-flex flex-row">
                                    Score: {movieContent.score}
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
           {myReview}

            <div className='container' >
                <h2>Reviews:</h2>
                {movieContent.reviews.map(review => <Review key={review.id} review={review}/>)}
            </div>
        </div>
    </>
  )
}
