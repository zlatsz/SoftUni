
const Message = ({name, message}) => {
    return (
        <>
            <tr className="cart-article">
        
                <td>
                    {name}
                </td>
                <td>
                    {message}
                </td>

            </tr>

        </>
    );

};

export default Message;