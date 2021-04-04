import { Link } from 'react-router-dom';
import "./index.css"

const OrderView = ({
    productId,
    productName,
    productPic,
    quantity,
    price,
    totalPrice
}) => {

   
    return (
        <>
            <tr className="cart-article">
                <td>
                    {productName}
                </td>
                <td>
                    <Link to={`/product/details/${productId}`}  ><img className="cart-image" src={productPic} alt="product" /> </Link>
                </td>
                <td>
                    {quantity}
                </td>
                <td>
                    {price} лв.
               </td>
                <td>
                    {totalPrice} лв.
                 </td>
            </tr>
          
        </>
    );

};

export default OrderView;