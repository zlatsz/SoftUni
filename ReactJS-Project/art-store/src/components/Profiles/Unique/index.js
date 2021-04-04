import "./index.css"

const Unique = ({
    userEmail,
    products
}) => {
    let size = Object.keys(products).length;
    let all = [];
    Object.keys(products).forEach(key => {
            all.push({ ...products[key] });
        });
  
    let totalQuantity = all.reduce(function(tot, arr) { 
        return +tot + +arr.quantity;
      },0);
    let sum = all.reduce(function(tot, arr) { 
        return tot + arr.totalPrice;
      },0);
    
    return (
        <>
            <tr className="cart-article">
                <td>
                    {userEmail}
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

export default Unique;