import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import * as productsService from '../../../services/productsService';
import "./index.css"

const ProductDetails = ({
    match,
    history

}) => {
    
    let [product, setProduct] = useState({});

    useEffect(() => {
        productsService.getOne(match.params.productId)
            .then(res => setProduct(res));
    }, [match.params.productId]);

    const onLikeButtonClickHandler = () => {
        let incrementedLikes = product.likes + 1;

        productsService.like(match.params.productId, incrementedLikes)
            .then((updatedProduct) => {
                setProduct(state => ({ ...state, likes: Number(updatedProduct.likes) }))
            });
    };

    const onSubmitHandler = function (e) {
        // e.preventDefault();
        // // const { productId, quantity, userId } = e.target;

        // // productsService.create(category.value, name.value, description.value, imageURL.value, price.value)
        // //     .then(() => {
        // //         history.push('/cart');
        // //     })
    };
    
    return (
        <>
            <header className="home-header">
                <Header />
                <h3 className="home-header-category-title">
                    Всички наши продукти се изработват ръчно от естествени съставки и материали!
                     </h3>
            </header>
            
            <section className="detail-products-list-item">
               
                <article className="detail-products-list-item-img"><img src={product.imageURL} alt="product" /></article>
                <h3> {product.name}</h3>
                <p className="detail-products-list-item-description">Описание: {product.description}</p>
                <p className="detail-products-list-item-price">{product.price} лв.</p>
               
                <form className="detail-products-list-item-cart" onSubmit={onSubmitHandler}>
                    <label htmlFor="quantity">Количество</label>
                    <input type="number" name="quantity" />

                    <button type="submit" className="detail-products-list-item-button" value="">Купи</button>
                </form>

                <p className="detail-products-list-item-likes"> {product.likes}
                    <button className="detail-products-list-item-likes-button" onClick={onLikeButtonClickHandler}>
                        <i className="fas fa-heart"></i>Харесай!
                        </button>
                </p>

                <div className="detail-products-list-item-admin">
                    <Link to={`/product/details/${match.params.productId}/edit`}><button className="detail-products-list-item-likes-button">Редактирай</button></Link>
                    <Link to={`/product/details/${match.params.productId}/delete`}><button className="detail-products-list-item-button">Изтрий</button></Link>

                </div>
            </section>
            <Footer />
        </>
    );
}

export default ProductDetails;