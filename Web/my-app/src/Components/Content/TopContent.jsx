import React, { Fragment } from 'react'
import { useEffect } from 'react'
import { useState } from 'react'
import Api from '../Api'
import Movie from './Movie'
import styles from './Movie.module.css'
import { Link } from 'react-router-dom'

export default function TopContent() {
    const [top, setTop] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetch = async () => {
            try {
                const data = await Api.getTop()
                console.log("Data Top Content")
                console.log(data)
                setTimeout(() => {
                    setLoading(false)
                    setTop(data.result)
                }, 800)
            } catch (e) {
                setLoading(false)
                setError('unexpected error fetching top content')
                console.log(e.message)
            }
        }
        fetch()
    }, [])

    if (error) {
        return <div>{error}</div>
    }

    return (
        <div className={styles.ContentTitle}>
            {loading 
                ? <div className={styles.MovieGrid}>LOADING...</div>
                : <>
                    <Link to={`/Content/Top`}>
                        <h1 > Top {top.length} Content</h1>
                    </Link>
                    <ol className={styles.MovieGrid}>{
                        top.map(content =>
                            <Movie key={content.id} movie={content} />)
                    }</ol>
                </>
            }


        </div>
    )
}
