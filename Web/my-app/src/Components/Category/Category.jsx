import React from 'react'
import {Link} from 'react-router-dom'

export default function Category({category}) {

  return (
    <li> 
      <Link to={`/categories/${category.id}`}> 
        {category.name}
      </Link>
    </li>
    )
}
