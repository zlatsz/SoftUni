import './index.css';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import Footer from "../Landing-page/Footer"

const AddProducts = () => {

    const onSubmitHandler = function (e) {
        e.preventDefault();
    };

    return (
        <>
            <section className="login-page">
                <Link to="/home" className="login-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                <article className="add-product-wrapper">
                    <h2>Добави продукт:</h2>
                    <form className="add-product-content" onSubmit={onSubmitHandler}>
                        <label htmlFor="category">Категория</label>
                        <select type="text" name="category" id="category" >
                            <option> jewellery </option>
                            <option> cosmetic </option>
                            <option> cart </option>
                            <option> other </option>
                        </select>

                        <label htmlFor="name">Име</label>
                        <input type="text" name="name" />

                        <label htmlFor="description">Описание</label>
                        <input type="textarea" name="description" />

                        <label htmlFor="imageURL">Снимка</label>
                        <input type="text" name="imageURL" />

                        <label htmlFor="price">Цена</label>
                        <input type="number" name="price" />

                        <button type="submit" className="add-product-submit" value="">Създай продукт</button>
                    </form>
                </article>
            </section>
            <Footer />
        </>
    );
}

export default AddProducts;