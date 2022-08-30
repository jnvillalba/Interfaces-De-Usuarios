import React from 'react'
import { useEffect } from 'react'
import { useState } from 'react'
import Api from '../Api'
import Category from './Category'
import styles from './Categories.module.css'

export default function Categories() {
    const [categories, setCategories] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetch = async () => {
            try {
                const data = await Api.getCategories()
                console.log("data categories")
                console.log(data)
                setTimeout(() => {
                    setLoading(false)
                    setCategories(data.result)
                }, 800)
            } catch (e) {
                setLoading(false)
                setError('unexpected error fetching categories')
                console.log(e.message)
             }
        }
        fetch()
    }, [])

    if (error) {
        return <div>{error}</div>
    }

    return (
        <>
            {loading
                ? <div className={styles.CategoryGrid}>LOADING...</div>
                : <section className={styles.CategoriesTitle}>
                    <h1>Categories: {categories.length}</h1>
                    <ul className={styles.Categories}>{
                        categories.map((category) => (
                            <Category key={category.id} category={category} />))
                    }
                    </ul>
                </section>
            }
        </>
    )
}

