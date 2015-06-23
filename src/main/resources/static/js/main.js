humane.info = humane.spawn({ addnCls: 'humane-flatty-info', timeout: 3000 });
humane.success = humane.spawn({ addnCls: 'humane-flatty-success', timeout: 3000 });
humane.error = humane.spawn({ addnCls: 'humane-flatty-error', timeout: 3000 });

document.addEventListener("DOMContentLoaded", function(event) {
    for(var i = 0; i < errors.length; ++i) {
        humane.error(errors[i])
    }
});
