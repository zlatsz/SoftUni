import { Link } from 'react-router-dom';
import "./index.css"

const OrderView = ({
    productName,
    quantity,
    price
}) => {

    return (

        <tr className="cart-article">
            <td>
                {productName}
            </td>
            {/* <div className="blog-image">
                <Link to={`/blog/${id}`}  ><img className="blog-article_image" src={imageURL} alt="post" /> </Link> 
                </div>
                <div className="blog-article_text">
                    <h3>{name}</h3>
                </div> */}
        </tr>

    );

};

export default OrderView;