import './index.css';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import Footer from "../Landing-page/Footer"

const AddPosts = () => {

    const onSubmitHandler = function (e) {
        e.preventDefault();
    };

    return (
        <>
            <section className="login-page">
                <Link to="/home" className="login-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                <article className="add-post-wrapper">
                    <h2>Добави статия:</h2>
                    <form className="add-post-content" onSubmit={onSubmitHandler}>

                        <label htmlFor="name">Име</label>
                        <input type="text" name="name" />

                        <label htmlFor="author">Автор</label>
                        <input type="textarea" name="author" />

                        <label htmlFor="imageURL">Съдържание</label>
                        <input type="text" name="imageURL" />


                        <button type="submit" className="add-post-submit" value="">Създай статия </button>
                    </form>
                </article>
            </section>
            <Footer />
        </>
    );
}

export default AddPosts;