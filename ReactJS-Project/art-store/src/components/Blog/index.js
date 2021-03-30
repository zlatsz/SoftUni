import './index.css';
import Header from "../Home/Navigation/Header";
import Footer from "../Home/Footer";
import { useEffect, useState } from 'react';
import * as postsService from '../../services/postsService';
import Post from "../Post/PostView";

const Blog = ({
    match,
    history

}) => {

    const [post, setPost] = useState({});

    useEffect(() => {
        postsService.getAll()
            .then(res => setPost(res));
    }, []);
    
   let data = Array.from(post);

    return (
        <>
            <header className="home-header">
                <Header />
                <h2 className="blog-header-category-title">
                    ЕТЕРИЧНИТЕ МАСЛА И ТЯХНОТО ВЛИЯНИЕ ЗА ЖИВОТ БЕЗ БОЛЕСТ
                     </h2>
            </header>
            <section className="blog-wrapper">
                <ul className="blog-wrapper-items">
                    {data.map(x =>
                        <Post key={x.id} {...x} />
                    )}
                </ul>

            </section>
            <Footer />
        </>
    );

};

export default Blog;