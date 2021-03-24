import { Link } from 'react-router-dom';

const Product = ({
    id,
    name,
    description,
    imageURL,
    category,
    price,
    likes,
}) => {

    return (
        <li className="home-products-list-item">
            <p className="home-products-list-item-img"><img src={imageURL} alt="product"/></p>
            <h3>Name: {name}</h3>
            <p className="home-products-list-item-price">{price}</p>
            {/* <p>Category: {category}</p> */}
            {/* <p className="home-products-list-item-description">{description}</p> */}
            <div className="home-products-list-item-info">
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-button">Виж</button></Link>
                <i className="fas fa-heart"></i> <span> {likes}</span>
            </div>
        </li>
    );
}

export default Product;