import React from 'react'
import Categories from './Category/Categories'
import LatestContent from './Content/LatestContent'
import TopContent from './Content/TopContent'
import './Review.css'
import '../App.css'

const Home = () => {

    return(
      <>
        <div className='header'><Categories/></div>
        <div className='row'>
          <div className='col-md-6'><TopContent/></div>
          <div className='col-md-6'><LatestContent/></div>
        </div>
      </>
    )
  }

export default Home

