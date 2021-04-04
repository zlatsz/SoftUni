import { Link } from 'react-router-dom';

const Order = ({id, products, userEmail}) => {
    let size = 0;
    let totalQuantity = 0;
    let sum = 0;
    if(products){
    size = Object.keys(products).length;
    let all = [];
    Object.keys(products).forEach(key => {
            all.push({ ...products[key] });
        });
  
    totalQuantity = all.reduce(function(tot, arr) { 
        return +tot + +arr.quantity;
      },0);
    sum = all.reduce(function(tot, arr) { 
        return tot + arr.totalPrice;
      },0);
    }
    return (
        <>
            <tr className="cart-article">
                <td>
                <Link to={`/cart/${id}`}>Виж пак</Link>
                </td>
                <td>
                    {size}
                </td>
                <td>
                    {Number(totalQuantity)} броя
                </td>
                <td>
                    {Number(sum)} лв.
               </td>
                <td>

                </td>
            </tr>

        </>
    );

};

export default Order;