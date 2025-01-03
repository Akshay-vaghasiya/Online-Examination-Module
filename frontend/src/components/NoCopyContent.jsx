import React, { useEffect } from 'react';

const NoCopyContent = ({ children }) => {
    return (
        <div 
            style={{ 
                userSelect: "none", 
                WebkitUserSelect: "none",
                msUserSelect: "none",
                MozUserSelect: "none"
            }}
        >
            {children}
        </div>
    );
};

export default NoCopyContent;
