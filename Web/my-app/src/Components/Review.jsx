import React from 'react'
import './Review.css'
import {Link} from 'react-router-dom'

export default function Review({review}) {

  return ( 
        <div className="p-2">
			<div className="card">
				<div className="row d-flex">
					<div>
						<Link to={`/user/${review.user.id}`}>
							<img className="profile-pic" src={review.user.image} alt={review.user}/>
							<h3 className="profile-name">{review.user.name}</h3>
						</Link>
					</div>
					<div>
						<Link to={`/content/${review.movie.id}`}>
							<img className='reviewImg' src={review.movie.poster} alt={review.movie.title} width="60px" />
							<h4>{review.movie.title}</h4>
						</Link>
						<h5>Stars: {review.stars}</h5>
					</div>
					<div className="p-4">
						<p>{review.text}</p>
					</div>
                </div>
			</div>
        </div>
    )
}
