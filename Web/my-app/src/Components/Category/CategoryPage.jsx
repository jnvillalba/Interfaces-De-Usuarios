import React from 'react'
import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Api from '../Api'
import styles from '../Content/Movie.module.css'
import Movie from '../Content/Movie'

export default function CategoryPage() {
    const { id } = useParams()
    const [categoryContent, setCategoryContent] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetch = async () => {
            try {
                const data = await Api.getCategoryById(id)
                console.log("data")
                console.log(data)
                setTimeout(() => {
                    setLoading(false)
                    setCategoryContent(data.result)
                }, 500)
                console.log("categoryContent")
                console.log(categoryContent)
            } catch (e) {
                setLoading(false)
                setError(e.message)
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
                    <div>
                        <h1>Movies: {categoryContent.length}</h1>
                    </div>
                    <ul className={styles.MovieGrid}>{
                        categoryContent.map(content =>
                            <Movie key={content.id} movie={content} />)
                    }</ul>

                </>
            }
        </div>
    )
}
