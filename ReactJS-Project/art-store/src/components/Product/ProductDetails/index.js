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
            <h2>Category: {category}</h2>
            <h3>Name: {name}</h3>
            <p className="home-products-list-item-img"><img src={imageURL} alt="product"/></p>
            <p className="home-products-list-item-description">{description}</p>
            <p className="home-products-list-item-price">{price}</p>
           
            <div className="home-products-list-item-likes">
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-like">Харесай</button></Link>
                <i className="fas fa-heart"></i> <span> {likes}</span>
            </div>

            <div className="home-products-list-item-cart">
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-quantity">Изтрий</button></Link>
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-button">Купи</button></Link>
            </div>

            <div className="home-products-list-item-admin">
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-delete">Изтрий</button></Link>
                <Link to={`/products/details/${id}`}><button className="home-products-list-item-edit">Редактирай</button></Link>
            </div>
        </li>
    );
}

export default Product;