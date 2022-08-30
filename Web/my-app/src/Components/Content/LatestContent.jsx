import React, { Fragment } from 'react'
import { useEffect } from 'react'
import { useState } from 'react'
import Api from '../Api'
import Movie from './Movie'
import styles from './Movie.module.css'
import { Link } from 'react-router-dom'

const LatestContent = () => {
    const [latest, setLatest] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)
    
    useEffect( () => {
        const fetch = async() => {
            try{
                const data = await Api.getLatest()
                console.log("data latest")
                console.log(data)
                setTimeout(() => {
                    setLoading(false)
                    setLatest(data.result)
                },800)
            } catch(e){
                setLoading(false)
                setError('unexpected error fetching latest content')
                console.log(e.message)
            } 
        }
        fetch()
    },[])

    if (error){
        return <div>{error}</div>
    }

    return (
      
            <div className={styles.ContentTitle}>
            {loading 
                ? <div className={styles.MovieGrid}>LOADING...</div>
                : <>
                <Link to={`/Content/Latest`}>
                    <h1>Latest Content</h1>
                </Link>
                <ul className={styles.MovieGrid}>{
                    latest.map(content => 
                        <Movie key={content.id} movie={content}/>)
                }</ul>
            </>
            }

        </div>
    )
}
export default LatestContent
