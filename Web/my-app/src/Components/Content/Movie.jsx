import React from 'react'
import {Link} from 'react-router-dom'
import styles from './Movie.module.css'

export default function Movie({movie}) {
  return (
    <li className={styles.MovieCard}>
        <Link to={`/content/${movie.id}`}>
            <img className={styles.MovieImg} src={movie.poster} alt={movie.title} />
            <div>{movie.title}</div>
        </Link>
    </li>
  )
}
