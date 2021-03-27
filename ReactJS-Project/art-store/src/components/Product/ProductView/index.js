import { Link } from 'react-router-dom';

const Product = ({
    id,
    name,
    imageURL,
    price,
    likes,
}) => {
    return (
        <li className="home-products-list-item">
            <article className="home-products-list-item-img">
                <img src={imageURL} alt="product" />
            </article>
            <h3>{name}</h3>
            <p className="home-products-list-item-price">{price} лв.</p>
            <i className="fas fa-heart"><span> {likes}</span></i> 
            <Link to={`/product/details/${id}`}><button className="home-products-list-item-button">Виж</button></Link>
        </li>
    );
}

export default Product;